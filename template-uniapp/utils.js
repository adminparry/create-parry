

export const router =  (url) => {
	uni.navigateTo({
		url,
		success: res => {},
		fail: () => {},
		complete: () => {}
	});
}
export const getPlatform = () => {
	return uni.getSystemInfoSync()
}

class QuickSort {
	
	
	sort(arr){
		this.quicksort(arr, 0, arr.length -1 );
	}
	quicksort(arr, low, high){
		if(low < high) {
			const p = this.partition(arr, low, high);
			this.quicksort(arr, low, p - 1);
			this.quicksort(arr, p + 1, high);
		}
	}
	partition(arr, low, high)  {
		const p = arr[high];
		
		let ret = low;
		for (var index = low; index < high; index++) {
			if(arr[index] < p) {
				this.swap(arr, ret, index);
				ret++;
			}
			
		}
		this.swap(arr, ret, high)
		return ret;
	}
	swap(arr, low, high) {
		const temp = arr[low];
		arr[low] = arr[high];
		arr[high] = temp;
	}
}

export default {
	router,
	getPlatform,
	QuickSort
}