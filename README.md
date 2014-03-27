## Configure Embedder: 

```java

  configuration().useStepdocReporter(new HTMLStepdocReporter(Paths.get("target/step-report")));

```

## Use embedder for reporting stepdocs:

```java

  new ReportEmbedder().configuredEmbedder().reportStepdocs();

```
