run:
	./gradlew runclient

data:
	./gradlew fmtData

quickdata:
	./gradlew rundata

fmt:
	./gradlew spotlessApply

update:
	git fetch
	git pull
