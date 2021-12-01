import {CurrentShipInfo} from "./dto/CurrentShipInfo";
import {Area} from "./dto/Area";
import {ShipMarker} from "./dto/ShipMarker";

export function shipMarkerReducer(
    ships: CurrentShipInfo[],
    boundingBox: Area): ShipMarker[] {
    
    let maxXDiff = Math.abs(Math.abs(boundingBox.fromX) - Math.abs(boundingBox.toX)) / 20;
    let maxYDiff = Math.abs(Math.abs(boundingBox.fromY) - Math.abs(boundingBox.toY)) / 20;
    console.log(maxXDiff, maxYDiff);
    let shipMarkers: ShipMarker[] = [];
    for(let ship of ships) {
        let closeMarker = shipMarkers.filter(m =>
            Math.abs(Math.abs(m.x) - Math.abs(ship.currentLocation.x)) <= maxXDiff &&
            Math.abs(Math.abs(m.y) - Math.abs(ship.currentLocation.y)) <= maxYDiff);
        if(closeMarker.length > 0) {
            closeMarker[0].ships.push(ship);
        } else {
            shipMarkers.push(new ShipMarker([ship], ship.currentLocation.x, ship.currentLocation.y));
        }
    }

    
    return shipMarkers;
}

