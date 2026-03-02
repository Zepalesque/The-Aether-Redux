run:
	./gradlew runclient

data:
	./gradlew rundata
	./gradlew spotlessApply

update:
	git fetch
	git pull
