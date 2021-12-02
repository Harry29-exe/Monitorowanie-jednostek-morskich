import {ShipIdDTO} from "./ships/ShipIdDTO";

export class ShipMarkerDTO {
  public ships: ShipIdDTO[];
  public x: number;
  public y: number;

  constructor(ships: ShipIdDTO[], x: number, y: number) {
    this.ships = ships;
    this.x = x;
    this.y = y;
  }

  public toMarkerString(): string {
    let str = "";
    let i = 0
    for(let ship of this.ships) {
      str = str.concat(`(${ship.name}, ${ship.mmsi})`);
      i++;
      if(i > 5) {
        str = str.concat("...and more")
        break;
      }
    }
    return str;
  }

}