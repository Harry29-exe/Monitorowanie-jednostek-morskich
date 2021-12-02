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

export function shipMarkerReducerSmallDistance(
  ships: CurrentShipInfo[],
  area: Area): ShipMarkerDTO[] {
    // debugger;
    const width = Math.abs(Math.abs(area.fromX) - Math.abs(area.toX));
    const height = Math.abs(Math.abs(area.fromY) - Math.abs(area.toY));
    const widthMul = width * 10;
    const heightMul = height * 10;
    let minX = area.minX < 0? area.minX + widthMul: area.minX - widthMul;
    let maxX = area.maxX < 0? area.maxX - widthMul: area.maxX + widthMul;
    let minY = area.minY < 0? area.minY + heightMul: area.minY - heightMul;
    let maxY = area.maxY < 0? area.maxY - heightMul: area.maxY + heightMul;

    let shipMarkers: ShipMarkerDTO[] = [];
    for(let ship of ships) {
        if(ship.currentLocation.x > minX && ship.currentLocation.x < maxX &&
            ship.currentLocation.y > minY && ship.currentLocation.y < maxY) {
            shipMarkers.push(new ShipMarkerDTO([ship], ship.currentLocation.x, ship.currentLocation.y));
        }
    }


    return shipMarkers;
}
