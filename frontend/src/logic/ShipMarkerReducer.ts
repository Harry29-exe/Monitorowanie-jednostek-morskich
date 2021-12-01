import {CurrentShipInfo} from "./dto/CurrentShipInfo";
import {Area} from "./dto/Area";
import {ShipMarker} from "./dto/ShipMarker";

export function shipMarkerReducer(
    ships: CurrentShipInfo[],
    boundingBox: Area): ShipMarker[] {
    
    let maxXDiff = Math.abs(boundingBox.fromX - boundingBox.toX) / 20;
    let maxYDiff = Math.abs(boundingBox.fromY - boundingBox.toY) / 20;
    
    let shipMarkers: ShipMarker[] = [];
    for(let ship of ships) {
        let closeMarker = shipMarkers.filter(m =>
            Math.abs(m.x - ship.currentLocation.x) <= maxXDiff &&
            Math.abs(m.y - ship.currentLocation.y) <= maxYDiff);
        if(closeMarker.length > 0) {
            closeMarker[0].ships.push(ship);
        } else {
            shipMarkers.push(new ShipMarker([ship], ship.currentLocation.x, ship.currentLocation.y));
        }
    }
    console.log(shipMarkers.length);

    
    return shipMarkers;
}

