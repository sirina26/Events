<template>
    <div class="container">
       

        <div class="card">
            <div class="card-header">
                Alert
            </div>

            <div class="card-body">
                <alert type="error">
                    An error alert
                </alert>

                <alert type="success">
                    A success alert
                </alert>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                Base Vue features
            </div>

            <div class="card-body">
                <basic-vue-features></basic-vue-features>
            </div>
        </div>
    </div>
</template>

<script>

import BasicVueFeatures from './BasicVueFeatures.vue'
import Alert from './Alert.vue'

export default {
    components: {
         BasicVueFeatures,
        Alert
       
    }
}
</script>

<style lang="scss" scoped>
    .card {
        margin-bottom: 50px;
    }
</style>