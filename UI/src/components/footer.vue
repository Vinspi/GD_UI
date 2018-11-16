<template>
  <div id="footer">
    <div id="foot-con">
      copyright Pierre VINCENT powered by
      <a href="https://vuejs.org/">vuejs</a>
      <a href="https://leafletjs.com/"> leaflet</a>
      <a href="https://www.chartjs.org/"> chart.js</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'vue-footer'
}
</script>

<style scoped>
  #footer {
    width: 100%;
    height: 7vh;
    background-color: #37474f;
    color: white;
    font-family: 'Ranga', cursive;
    text-align: center;
    font-size: 15pt;
    -webkit-box-shadow: 0px -3px 3px 0px rgba(55,71,79,1);
    -moz-box-shadow: 0px -3px 3px 0px rgba(55,71,79,1);
    box-shadow: 0px -3px 3px 0px rgba(55,71,79,1);
  }
  #foot-con {
    margin-top: 2vh;
  }
</style>
