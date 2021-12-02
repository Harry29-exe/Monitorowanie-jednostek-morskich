export class Area {
  public fromX: number;
  public fromY: number;
  public toX: number;
  public toY: number;


  constructor(fromX: number, fromY: number, toX: number, toY: number) {
    this.fromX = fromX;
    this.fromY = fromY;
    this.toX = toX;
    this.toY = toY;
  }

  // public inBounds(x: number, y: number): boolean {
  //
  // }

  get minX(): number {
    return Math.min(this.fromX, this.toX);
  }

  get maxX(): number {
    return Math.max(this.fromX, this.toX);
  }

  get minY(): number {
    return Math.min(this.fromY, this.toY);
  }

  get maxY(): number {
    return Math.max(this.fromY, this.toY);
  }

}