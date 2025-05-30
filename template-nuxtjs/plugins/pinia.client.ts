
import plugin from 'pinia-plugin-persistedstate'


export default defineNuxtPlugin((context: any) => {
    console.log(context)
    context.$pinia.use(plugin)

})