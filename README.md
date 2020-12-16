# Car Park Vacancy Checker

Fun app to check if there are vacant car park slots near our unit!

## Run tests
```shell script
./gradlew test
```

## Build image using Cloud Build
```shell script
gcloud builds submit --tag gcr.io/carparkvacancychecker/carparkvacancychecker
```

### Deploy image on GCP
```shell script
 gcloud run deploy --image gcr.io/carparkvacancychecker/carparkvacancychecker --platform managed
```
