FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive
ENV TZ Asia/Tokyo

RUN apt-get update && \
    apt-get install -y binutils build-essential sysstat strace \
    golang jq vim tzdata

WORKDIR /app

COPY . .
