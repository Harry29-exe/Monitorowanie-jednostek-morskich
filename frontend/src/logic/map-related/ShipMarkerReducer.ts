import {CurrentShipInfo} from "../dto/ships/CurrentShipInfo";
import {Area} from "../dto/Area";
import {ShipMarkerDTO} from "../dto/ShipMarkerDTO";

export function shipMarkerReducer(
    ships: CurrentShipInfo[],
    boundingBox: Area): ShipMarkerDTO[] {
    let maxXDiff = Math.abs(Math.abs(boundingBox.fromX) - Math.abs(boundingBox.toX)) / 20;
    let maxYDiff = Math.abs(Math.abs(boundingBox.fromY) - Math.abs(boundingBox.toY)) / 20;
    let shipMarkers: ShipMarkerDTO[] = [];
    for(let ship of ships) {
        let closeMarker = shipMarkers.filter(m =>
            Math.abs(Math.abs(m.x) - Math.abs(ship.currentLocation.x)) <= maxXDiff &&
            Math.abs(Math.abs(m.y) - Math.abs(ship.currentLocation.y)) <= maxYDiff);
        if(closeMarker.length > 0) {
            closeMarker[0].ships.push(ship);
        } else {
            shipMarkers.push(new ShipMarkerDTO([ship], ship.currentLocation.x, ship.currentLocation.y));
        }
    }

    
    return shipMarkers;
}

