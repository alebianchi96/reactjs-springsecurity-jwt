# Use a lightweight HTTP server as the base image
FROM nginx:alpine

# Copy the built React app from the host into the NGINX server's root directory
COPY build /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

# Expose port 80 to the outside world
EXPOSE 80

# Command to run the NGINX server
CMD ["nginx", "-g", "daemon off;"]