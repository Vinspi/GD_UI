<template>
  <div id="app">
    <h1 id="title">Sensors data from the city of Aarhus, Danemark</h1>
    <router-view/>
    <vue-footer/>
  </div>
</template>

<script>

import footer from '@/components/footer'
export default {
  name: 'app',
  components: {
    'vue-footer': footer
  }
}

</script>

<style>
h1 {
  font-size: 25pt;
}
h2 {
  font-size: 20pt;
  color: white;
}
.line {
  display: flex;
  flex-direction: row !important;
}

.col {
  display: flex;
  flex-direction: column !important;
}
.datepicker-day-button {
  color: black !important;
}
#title {
  text-align: center;
  background-color: #102027;
  width: 100%;
  margin: 0px;
  margin-bottom: 25px;
  padding: 20px;
  color: white;
  -webkit-box-shadow: 0px 3px 3px 0px rgba(16,32,39,1);
  -moz-box-shadow: 0px 3px 3px 0px rgba(16,32,39,1);
  box-shadow: 0px 3px 3px 0px rgba(16,32,39,1);
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
body {
  background-color: #37474f;
  margin: 0px;
}
</style>
