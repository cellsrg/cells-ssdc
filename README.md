# cells-ssdc
Research System for Rule-Based Canonicalization of Arbitrary Tables in Spreadsheets

## Build with Apache maven

In order to build package executable JAR file run the following command:

```shell
mvn clean compile assembly:single
```

Then You can run a script program to ensure the build process worked.

```shell
./ssdc.sh
```

If the program outputs (at the end) something like
```
Total number of
	tables: 3
	cells: 75
	not empty cells: 72
	labels: 36
	entries: 36
	label-label pairs: 0
	entry-label pairs: 144
	category-label pairs: 36
	categories: 12
	label groups: 12

Total rule firing time: 73

End timestamp: 2017-03-31 00:19:42.996
```
then it works.

There is a Makefile that can be used to build project's artifacts (now it is just the stand-alone executable JAR).

```bash
make build    # build executable JAR
make run      # Run the script
```

License: Apache-2.0.
