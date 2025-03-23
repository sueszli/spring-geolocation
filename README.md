A Spring backend project for handling geolocation data.

# Create Location

Create a new location by providing the following details:

- `name`: String
- `type`: String ("premium" or "standard")
- `lat`: Double (latitude)
- `lng`: Double (longitude)

The system will assign and return an `id` for the new location.

```json
{
  "name": "Fancy Place",
  "lat": 48.2,
  "lng": 15.6,
  "type": "premium"
}
```

# Search Location

Search for locations based on:

- `type`: String (optional)
- Rectangular area: `p1` and `p2` coordinates (optional)
- `limit`: Integer (optional)

Results are ordered by type, with "premium" locations first.

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
