FROM ubuntu:16.04

RUN apt-get update && \
    apt-get install binutils build-essential \
    sysstat -y

WORKDIR /app

COPY . .
