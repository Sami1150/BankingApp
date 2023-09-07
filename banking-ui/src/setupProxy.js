const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      // 👇️ make sure to update your target
      target: 'http://localhost:8080/',
      changeOrigin: true,
    }),
  );
  app.use(
    '/login',
    createProxyMiddleware({
      // 👇️ make sure to update your target
      target: 'http://localhost:8080',
      changeOrigin: true,
    }),
  );
  app.use(
    '/login?logout',
    createProxyMiddleware({
      // 👇️ make sure to update your target
      target: 'http://localhost:8080',
      changeOrigin: true,
    }),
  )
  app.use(
    '/logout',
    createProxyMiddleware({
      // 👇️ make sure to update your target
      target: 'http://localhost:8080',
      changeOrigin: true,
    }),
  );
  app.use(
    '/login?logout',
    createProxyMiddleware({
      // 👇️ make sure to update your target
      target: 'http://localhost:8080/',
      changeOrigin: true,
    }),
  );
  app.use(
    '/bankingapp',
    createProxyMiddleware({
      // 👇️ make sure to update your target
      target: 'http://localhost:8080/',
      changeOrigin: true,
    }),
  );
};
