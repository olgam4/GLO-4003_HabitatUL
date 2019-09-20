#!/bin/bash

eval "$(ssh-agent -s)"
chmod 600 .travis/id_rsa
ssh-add .travis/id_rsa

git config --global push.default matching
git remote add deploy git@github.com:GLO4003UL/a19-eq6.git
git push -f deploy master
