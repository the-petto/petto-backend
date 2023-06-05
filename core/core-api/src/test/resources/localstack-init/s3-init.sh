#!/usr/bin/env bash

awslocal s3api create-bucket \
  --bucket test \
  --region ap-northeast-2 \
  --create-bucket-configuration LocationConstraint=us-east-2

awslocal s3api list-buckets --query "Buckets[].Name"
