You are asked to provide a backend application that handles geolocation data.

Create a REST API that handles locations.

If requests come with a payload, the API should accept a json and should return a json as well, if a response is required.

## CREATE: Create a Location

It should be possible to create a new location by providing a `name`, a `type` with possible values `premium` or `standard`
and coordinates using `lat` and `lng`.

The system has to assign the location an `id` and return it back within the response.

Here some example values to create a location:

```json
{
  "name": "Fancy Place",
  "lat": 48.2,
  "lng": 15.6,
  "type": "premium"
}
```

## SEARCH: Search for a Location

It should be possible to search for locations based on `type` or by defining a rectangular area with two points `p1`, `p2`
(`lat`,`lng`) and returning all locations within it.

It is also possible to use both criteria together and the result set might be limited by `limit`.

Results must be ordered by type where `premium` ones come first.

Here is an example to search for a location:

```json
{
    "type": "premium",
    "p1": {
        "lat": 46.6,
        "lng": 15.4
    },
    "p2": {
        "lat": 48.8,
        "lng": 17.5
    },
    "limit": 3
}
```
