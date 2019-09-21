#!/bin/bash

openssl aes-256-cbc -K $encrypted_27a7b75a5f77_key -iv $encrypted_27a7b75a5f77_iv -in .travis/id_rsa.enc -out .travis/id_rsa -d
ls .travis

eval "$(ssh-agent -s)"
chmod 600 .travis/id_rsa
ssh-add .travis/id_rsa

git config --global push.default matching
git remote add deploy git@github.com:GLO4003UL/a19-eq6.git
git push -f deploy master
