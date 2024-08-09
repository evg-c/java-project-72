.DEFAULT_GOAL := build-run

clean:
	make -C app clean

build:
	make -C app build

install:
	make -C app install

run-dist:
	make -C app run-dist

lint:
	make -C app lint

test:
	make -C app test

report:
	make -C app report

update-deps:
	make -C app update-deps

build-run: build run

.PHONY: build

