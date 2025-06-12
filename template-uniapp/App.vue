<script>
	export default {
		data(){
			return {
				platform: {}
			}
			
		},
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
			this.platform = uni.getSystemInfoSync()
			console.log(this.platform.model)

		},
		onHide: function() {
			console.log('App Hide')
		},
		onLoad() {
			
		}
	}
</script>

<style>
	/*每个页面公共css */
</style>
