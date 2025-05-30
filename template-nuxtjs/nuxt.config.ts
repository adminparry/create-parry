// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    modules: ['@nuxtjs/tailwindcss', '@pinia/nuxt'],
    plugins: [
        { src: "@/plugins/pinia.client.ts", mode: "client" }
    ],
    css: [
        '@/assets/tailwind.css'
    ],
    runtimeConfig: {
        public: {
            port: 4000
        }
    },
    vite: {
        
        server: {
            proxy: {
                '/api/': {
                    target: 'http://localhost:3000/',
                    changeOrigin: true,
                }
            },
        }
    },
    app: {
        pageTransition: { name: 'page', mode: 'out-in' },
        head: {
            meta: [
                // <meta name="viewport" content="width=device-width, initial-scale=1">
                { name: 'viewport', content: 'width=device-width, initial-scale=1' }
            ],
            script: [
                // <script src="https://myawesome-lib.js"></script>
                // { src: 'https://awesome-lib.js' }
            ],
            link: [
                // <link rel="stylesheet" href="https://myawesome-lib.css">
                // { rel: 'stylesheet', href: 'https://awesome-lib.css' }
            ],
            // please note that this is an area that is likely to change
            style: [
                // <style type="text/css">:root { color: red }</style>
                // { children: ':root { color: red }', type: 'text/css' }
            ],
            noscript: [
                // <noscript>Javascript is required</noscript>
                { children: 'Javascript is required' }
            ]
        }
    }
})
