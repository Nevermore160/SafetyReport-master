<template>
  <div class="menu">
    <div class="dropdown">
      <span>Mouse over me</span>
      <div class="dropdown-content">
        <p>Hello World!</p>
      </div>
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style scoped>
.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #0e176b;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  padding: 12px 16px;
  z-index: 1;
}

.dropdown:hover .dropdown-content {
  display: block;
}
.menu {
  width: 6rem;
  float: left;
  height: 100%;
}
</style>
