import {ShipIdDTO} from "./ShipIdDTO";

export interface ShipDTO extends ShipIdDTO {
  publicId: any;
  shipType: string;
  stillTracked: boolean;
}