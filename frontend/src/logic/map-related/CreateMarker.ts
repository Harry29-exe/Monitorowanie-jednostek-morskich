export function createMarker(shipsCount: number): HTMLElement {
  let size = shipsCount === 1 ? 10 : shipsCount > 20 ? 16 : shipsCount > 5 ? 14 : 12;
  let el = document.createElement("div") as HTMLDivElement;
  el.style["borderRadius"] = "100%";
  el.style["marginLeft"] = `${-size / 2}px`;
  el.style["marginTop"] = `${-size / 2}px`;
  el.style["width"] = `${size}px`;
  el.style["height"] = `${size}px`;
  el.style["background"] = shipsCount === 1 ? "blue" :
    shipsCount > 20 ? "rgb(115, 50, 168, 0.6)" :
      shipsCount > 5 ? "rgb(201, 209, 48, 0.8)" : "rgb(48, 209, 61, 0.95)";
  el.style["border"] = "1px solid black"

  return el;
}