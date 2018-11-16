<template>
  <div id="charts" class="col">
    <h1>Data for Node lat: {{node.lat}}, lon: {{node.lon}}</h1>
    <div class="line">
      <div class="chartV chartContainer">
        <canvas id="vehicle"/>
      </div>
    </div>
    <div class="line">
      <div style="color: white;">
        Period: from
        <div class="input-field inline">
          <input v-model="period.from" style="color: white;" type="text" class="datepicker" id="from">
        </div>,
        <div class="input-field inline">
          <input v-model="period.fromTime" style="color: white;" type="text" class="timepicker" id="timefrom">
        </div>
        to
        <div class="input-field inline">
          <input v-model="period.to" style="color: white;" type="text" class="datepicker" id="to">
        </div>,
        <div class="input-field inline">
          <input v-model="period.toTime" style="color: white;" type="text" class="timepicker" id="timeto">
        </div>
        <a style="margin-left: 20px;" class="btn" @click="update()">update</a>
      </div>
    </div>
    <div class="line">
      <div class="climateChart chartContainer">
        <canvas id="temperature"/>
      </div>
      <div class="climateChart chartContainer">
        <canvas id="pressure"/>
      </div>
    </div>
    <div class="line">
      <div class="climateChart chartContainer">
        <canvas id="humidity"/>
      </div>
      <div class="climateChart chartContainer">
        <canvas id="wind-speed"/>
      </div>
    </div>
  </div>
</template>

<script>
import chart from 'chart.js'
import moment from 'moment'
import $ from 'jquery'
export default {
  name: 'charts',
  props: ['data', 'node'],
  data(){
    return {
      chartVehicle: {},
      tempChart: {},
      chumidityChart: {},
      windChart: {},
      pressureChart: {},
      datasetTemperature: {},
      period: {
        from: '2014-08-14',
        fromTime: '01:00',
        to: '2014-09-24',
        toTime: '17:50'
      }
    }
  },
  computed: {
    formatTimeFrom: function() {
      return this.period.from+'T'+this.period.fromTime+':00'
    },
    formatTimeTo: function() {
      return this.period.to+'T'+this.period.toTime+':00'
    }
  },
  methods: {
    async update() {

        this.period.from = $('#from').val();
        this.period.to = $('#to').val();
        this.period.fromTime = $('#timefrom').val();
        this.period.toTime = $('#timeto').val();


        var datasetTemperature = await this.loadTemperature(this.formatTimeFrom, this.formatTimeTo);
        var datasetPressure = await this.loadPressure(this.formatTimeFrom, this.formatTimeTo);
        var datasetHumidity = await this.loadHumidity(this.formatTimeFrom, this.formatTimeTo);
        var datasetWind = await this.loadWindSpeed(this.formatTimeFrom, this.formatTimeTo);

        this.tempChart.data = datasetTemperature;
        this.pressureChart.data = datasetPressure;
        this.humidityChart.data = datasetHumidity;
        this.windChart.data = datasetWind;

        this.tempChart.update();
        this.pressureChart.update();
        this.windChart.update();
        this.humidityChart.update();

    },
    processData(dataServer, bgColor, borderColor, lab) {

      /* process data here to fit chart.js dataset */

      var d = [];
      var data = [];
      var labs = [];


      var bindings = dataServer.results.bindings;


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
              label: lab,
              backgroundColor: bgColor,
              borderColor: borderColor,
              data: data,
          }]
      }

      return dataset;
    },

    async loadPressure(from, to){

      var dataTemp = await $.ajax({
        url: 'http://localhost:8090/getPressure',
        type: 'GET',
        data: {
          from: from,
          to: to
        }
      })

      dataTemp = JSON.parse(dataTemp);

      return this.processData(dataTemp, 'rgba(255, 99, 132, 0)', 'rgb(37, 188, 55)', 'Pressure (Bar)');
    },
    async loadTemperature(from, to){

      var dataTemp = await $.ajax({
        url: 'http://localhost:8090/getTemperature',
        type: 'GET',
        data: {
          from: from,
          to: to
        }
      })

      dataTemp = JSON.parse(dataTemp);

      return this.processData(dataTemp, 'rgba(255, 99, 132, 0)', 'rgb(255, 99, 132)', 'Temperature (Celsius)')

    },
    async loadWindSpeed(from, to){

      var dataTemp = await $.ajax({
        url: 'http://localhost:8090/getWindSpeed',
        type: 'GET',
        data: {
          from: from,
          to: to
        }
      })

      dataTemp = JSON.parse(dataTemp);

      return this.processData(dataTemp, 'rgba(255, 99, 132, 0)', 'rgb(219, 72, 59)', 'Wind Speed (km-per-hour)')

    },
    async loadHumidity(from, to){

      var dataTemp = await $.ajax({
        url: 'http://localhost:8090/getHumidity',
        type: 'GET',
        data: {
          from: from,
          to: to
        }
      })

      dataTemp = JSON.parse(dataTemp);

      return this.processData(dataTemp, 'rgba(255, 99, 132, 0)', 'rgb(214, 151, 74)', 'Humidity (percent)')

    },
    async initCharts(){
      var ctx = document.getElementById("vehicle");
      var ctx2 = document.getElementById("temperature");
      var ctx3 = document.getElementById("pressure");
      var ctx4 = document.getElementById('wind-speed');
      var ctx5 = document.getElementById('humidity');


      this.chartVehicle = new Chart(ctx, {
        type: 'line',
        data: this.data
      })


      var datasetTemperature = await this.loadTemperature(this.formatTimeFrom, this.formatTimeTo);

      this.tempChart = new Chart(ctx2, {
        type: 'line',
        data: datasetTemperature
      })

      var datasetPressure = await this.loadPressure(this.formatTimeFrom, this.formatTimeTo);

      this.pressureChart = new Chart(ctx3, {
        type: 'line',
        data: datasetPressure
      })

      var datasetHumidity = await this.loadHumidity(this.formatTimeFrom, this.formatTimeTo);

      this.humidityChart = new Chart(ctx5, {
        type: 'line',
        data: datasetHumidity
      })

      var datasetWindSpeed = await this.loadWindSpeed(this.formatTimeFrom, this.formatTimeTo);

      this.windChart = new Chart(ctx4, {
        type: 'line',
        data: datasetWindSpeed
      })
    }
  },
  mounted() {
    this.initCharts();
    M.Datepicker.init($('#from'), {
      defaultDate: new Date(this.period.from),
      format: 'yyyy-mm-dd'
    });

    M.Datepicker.init($('#to'), {
      defaultDate: new Date(this.period.to),
      format: 'yyyy-mm-dd'
    });

    M.Timepicker.init($('.timepicker'), {
      format: 'hh:mm',
      twelveHour: false
    });

  },
  watch: {
    data: function(oldP, newP) {
      this.chartVehicle.data = newP;
      this.chartVehicle.update();
    },

  }
}
</script>

<style scoped>
h1 {
  margin-bottom: 5vh;
  color: white;

}

.line {
  justify-content: space-around;
}

#charts {
  background-color: #102027;
  display: flex;
  width: 100%;
  height: 100%;
  margin-top: 10vh;
  -webkit-box-shadow: 0px -3px 3px 0px rgba(16,32,39,1);
  -moz-box-shadow: 0px -3px 3px 0px rgba(16,32,39,1);
  box-shadow: 0px -3px 3px 0px rgba(16,32,39,1);
}
.chartV {
  width: 80%;
  height: 100%;
}
.climateChart {
  width: 45%;
  height: 100%;
}

</style>
