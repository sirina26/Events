<template>
    <div>
        <div class="mb-4">
            <h1 v-if="mode == 'comment'">Créer une proposition</h1>
            <h1 v-else>Editer une proposition</h1>
        </div>

        <form @submit="onSubmit($event)">           

            <template v-if="item !== null">
                <div class="form-group">
                    <label class="required">Ma Proposition</label>
                    <input type="text" v-model="item.proposition" class="form-control" required>
                </div>                             
            </template>
            
            <button type="submit" class="btn btn-primary">Sauvegarder</button>

        </form>
    </div>
</template>

<script>
    import { getCommentAsync, createCommentAsync, updateCommentAsync } from '../../api/commentApi'
    import { DateTime } from 'luxon'

    export default {
        data () {
            return {
                item: {},
                mode: null,
                eventid: null,
                propDate: null,
                errors: [],
            }
        },

        async mounted() {
            
            this.mode = this.$route.params.mode;
            this.eventid = this.$route.params.id;
            this.item.eventid = this.$route.params.id;

            if(this.mode == 'editComment') {
                try {
                    const item = await getCommentAsync(this.commentid);
                    
                    this.item = item;
                 
                }
                catch(e) {
                    console.error(e);
                    debugger;
                    this.$router.replace('/.');
                }
            }
             console.log(this.item); 
          
        },

        methods: {
            async onSubmit(event) {
            event.preventDefault();
            this.item.eventid = this.$route.params.id;

                var errors = [];
                
                if(errors.length == 0) {
                    try {
                        if(this.mode == 'editComment') {
                            await updateCommentAsync(this.item);
                        }

                        else {
                            await createCommentAsync(this.item);
                        }
                        this.$router.replace('/event');
                    }
                    catch(e) {
                        console.error(e);
                    }
                }
            }
        }
    }
</script>

<style lang="scss">

</style>