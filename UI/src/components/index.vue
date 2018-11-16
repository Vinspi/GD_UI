<template>
  <div id="index">
    <h2>Click on a cursor on the map to retrieve data then scroll down</h2>
    <div id="map"/>
    <charts v-if="dataLoaded" :data="data" :node="nodeName"/>
  </div>
</template>

<script>
import moment from 'moment'
import charts from '@/components/charts'
import L from 'leaflet'
import $ from 'jquery'
export default {
    name: 'index',
    data(){
      return {
        map: {},
        data: {},
        nodeName: '',
        dataLoaded: false
      }
    },
    methods: {
      initMap() {
        this.map = L.map('map').setView([56.162939, 10.203921], 13);


        var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', { // LIGNE 16
            attribution: '© OpenStreetMap contributors',
            maxZoom: 19
        });

        // var marker = L.marker([56.162939, 10.203921]).addTo(this.map);

        this.map.addLayer(osmLayer);


      },
      async retrieveDataFromPoint(coords){

        this.nodeName = coords;

        var resp = await $.ajax({
          url: 'http://localhost:8090/pointData',
          type: 'GET',
          data: {
            lat: coords.lat,
            lon: coords.lon
          }
        })

        resp = JSON.parse(resp)

        /* process data here to fit chart.js dataset */

        var d = [];
        var data = [];
        var labs = [];


        var bindings = resp.results.bindings;


        for(var i=0;i<bindings.length;i++) {
          d.push({data: parseInt(bindings[i].value.value), time: moment(bindings[i].time.value)})
        }

        /* d contains date but we have to sort them to draw a good chart */

        d.sort(function (left, right) {
          return left.time.diff(right.time, 'hours');
        })

        /* now we can parse data */

        for(var i=0;i<d.length;i++){
          data.push(d[i].data);
          labs.push(d[i].time.format());
        }


        var dataset = {
            labels: labs,
            datasets: [{
                label: "Vehicle count",
                backgroundColor: 'rgba(255, 99, 132, 0.3)',
                borderColor: 'rgb(255, 99, 132)',
                data: data,
            }]
        }

        this.data = dataset;
        this.dataLoaded = true;

      },
      onMarkerClick(e){
        var coords = {lat: e.latlng.lat, lon: e.latlng.lng};

        this.retrieveDataFromPoint(coords)

      },
      async retrievePoints() {

        var resp = await $.ajax({
          url: 'http://localhost:8090/points'
        })

        this.data = JSON.parse(resp);


      },
      drawPoints(){

        var bindings = this.data.results.bindings

        for(var i=0;i<bindings.length;i++){
          L.marker([bindings[i].lat.value, bindings[i].lon.value]).addTo(this.map).on('click', this.onMarkerClick);
        }

      }
    },
    async mounted(){
      this.initMap();
      await this.retrievePoints();
      this.drawPoints();

      var coords = {lat: this.data.results.bindings[0].lat.value, lon: this.data.results.bindings[0].lon.value};

      this.retrieveDataFromPoint(coords)

    },
    components: {
      charts: charts
    }
}
</script>

<style scoped>
#map {
  height: 700px;
  width: 1200px;
  margin-left: auto;
  margin-right: auto;
}
</style>
