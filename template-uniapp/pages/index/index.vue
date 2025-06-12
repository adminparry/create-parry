<template>

	<view class="content">
		<!-- #ifdef  MP -->
		<image class="logo" src="/static/logo.png"></image>
		<!-- #endif -->
		<view class="text-area" @click="toHeader">
			<text class="title">{{title}}</text>
		</view>
		 {{ platform.uniPlatform }}
		<view>flex layout</view>
		<view class="row">
			<view class="block">1</view>
			<view class="block">2</view>
			<view class="block">3</view>
			<view class="block">4</view>
		</view>


		
		<view>grid layout</view>
		<view class="container">
			<view class="item"></view>
			<view class="item"></view>
			<view class="item"></view>
			<view class="item"></view>
			<view class="item"></view>
			<view class="item"></view>
			<view class="item"></view>
			<view class="item"></view>
			<view class="item"></view>
		</view>
		
	</view>
</template>

<script>
	import utils from '../../utils.js'
	export default {
		data() {
			return {
				title: 'Hello',
				platform: utils.getPlatform()
			}
		},
		onLoad() {
			
			const u = new utils.QuickSort();

			const arr = [1,3,5,6,3,2,1]
			u.sort(arr);
			console.log(arr)
		},
		methods: {
			toHeader(){

				utils.router("/pages/header/header")
			}
		}
	}
</script>

<style>
	.row {
		display: flex;
		flex-direction: row;
		justify-content: space-evenly;
		background-color: #8f8f94;
		width: 100%;
	}
	.container {

		grid-row-gap: 24px;
		/*列间距*/
		grid-column-gap: 24px;
	  display: grid; /* 或 inline-grid */
	  grid-template-columns: repeat(3,100px);
	  grid-template-rows: repeat(3,50px);

	}
	.item {
	    background: #33a8a5;
	  }
	
	.column {
		display: flex;
		flex-direction: column;
	}
	.block  {
		display: block;
	}
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
