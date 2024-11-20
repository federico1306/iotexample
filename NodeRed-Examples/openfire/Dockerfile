# Use Ubuntu as the base image
FROM ubuntu:latest
 
# Set environment variables
ENV OPENFIRE_VERSION=4.7.5
ENV OPENFIRE_HOME=/opt/openfire
 
# Update apt and install dependencies
RUN apt-get update
 
RUN apt-get install -y --no-install-recommends \
    openjdk-11-jre-headless \
    wget \
    bash \
    tzdata
 
# Create the Openfire home directory
RUN mkdir -p $OPENFIRE_HOME
 
# Navigate to /tmp and download Openfire
WORKDIR /tmp
RUN echo "Downloading Openfire version: $OPENFIRE_VERSION" && \
    wget https://github.com/igniterealtime/Openfire/releases/download/v4.9.2/openfire_4_9_2.tar.gz -O openfire.tar.gz
 
# Extract the Openfire tarball
RUN tar -xzf openfire.tar.gz -C $OPENFIRE_HOME --strip-components=1
 
# Clean up downloaded tarball
RUN rm openfire.tar.gz
 
# Create openfire user and group
RUN groupadd -r openfire && \
    useradd -r -g openfire openfire
 
# Change ownership of the Openfire directory
RUN chown -R openfire:openfire $OPENFIRE_HOME
RUN chmod +x $OPENFIRE_HOME/bin/openfire.sh
# Clean up apt cache to reduce image size
RUN apt-get clean && rm -rf /var/lib/apt/lists/*
 
# Set the working directory
WORKDIR $OPENFIRE_HOME
 
# Expose necessary ports
EXPOSE 9090 9091 5222 5269
 
# Switch to the openfire user
USER openfire
 
# Set the default command
CMD ["bash", "bin/openfire.sh"]
 