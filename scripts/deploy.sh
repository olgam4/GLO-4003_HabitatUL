#!/bin/bash

openssl aes-256-cbc -K $encrypted_27a7b75a5f77_key -iv $encrypted_27a7b75a5f77_iv -in .travis/id_rsa.enc -out .travis/id_rsa -d

ls .travis

chmod 600 .travis/id_rsa

eval "$(ssh-agent -s)"
ssh-add .travis/id_rsa

echo "DEPLOY REPO"

git config --global push.default matching
git fetch --unshallow
git remote add deploy git@github.com:GLO4003UL/a19-eq6.git
git push -f deploy master

echo "DEPLOY WIKI"

git clone https://github.com/SamuelCabralCruz/GLO-4003.wiki.git GLO-4003.wiki
cd GLO-4003.wiki
git remote add wiki git@github.com:GLO4003UL/a19-eq6.wiki.git
rm Home.md
git commit --all --amend --no-edit
git push -f wiki master
cd ..
rm -rf GLO-4003.wiki
