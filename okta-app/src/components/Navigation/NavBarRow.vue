<template>
  <ui-item active>
    <ui-item-first-content>
      <ui-icon>{{ iconName }}</ui-icon>
    </ui-item-first-content>
    <ui-item-text-content>{{ labelName }}</ui-item-text-content>
  </ui-item>
</template>

<script>



export default {
  props: ['iconName', 'labelName']
}

</script>

<style scoped>
</style>