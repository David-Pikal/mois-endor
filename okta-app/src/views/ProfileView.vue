<template>
  <div class="container-title">
    <h2>USER INFO</h2>
  </div>

  <div v-shadow="6">
    <table class="table">
      <tr class="row">
        <td><h3>NAME</h3></td>
        <td>{{ user.name }}</td>
      </tr>
      <tr class="row">
        <td><h3>EMAIL</h3></td>
        <td>{{ user.email }}</td>
      </tr>
      <tr class="row">
        <td><h3>USER ID</h3></td>
        <td>{{ user.sub }}</td>
      </tr>
      <tr class="row">
        <td><h3>VERIFIED</h3></td>
        <td v-if="user.email_verified">YES</td>
        <td v-else>NO</td>
      </tr>
    </table>
  </div>

</template>

<script>

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'profile',
  data () {
    return {
      user: Object
    }
  },
  async created () {
    this.user = await this.$auth.getUser()
  }
}
</script>

<style scoped>

.container-title {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.row {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}

.table {
  width: 70%;
  margin: auto;
  padding: 20px;
}


</style>