import React, {useState} from 'react';
import {HStack, IconButton, Tooltip, VStack} from "@chakra-ui/react";
import {MinusIcon, SpinnerIcon, ViewIcon, ViewOffIcon} from "@chakra-ui/icons";
import {ShipDTO} from "../../logic/dto/ships/ShipDTO";
import {OperationStatus} from "./UserModuleWrapper";
import ShipInfoModal from "./ShipInfoModal";

enum State {
  VISIBLE,
  NOT_VISIBLE,
  CHANGING
}

const ShipExplorerRow = (props: {
  ship: ShipDTO,
  show: (id: string) => Promise<OperationStatus>,
  hide: (id: string) => Promise<OperationStatus>
}) => {
  const [state, setState] = useState<State>(State.NOT_VISIBLE);
  const ship = props.ship;

  const onVisibilityChange = () => {
    setState(State.CHANGING);
    if(state === State.VISIBLE) {
      props.hide(ship.publicId).then(status => setState(
          status === OperationStatus.OK ? State.NOT_VISIBLE : State.VISIBLE))

    } else if(state === State.NOT_VISIBLE) {
      props.show(ship.publicId).then(status => setState(
        status === OperationStatus.OK? State.VISIBLE: State.NOT_VISIBLE
      ));

    } else {
      return;
    }
  }


  return (
    <HStack spacing={10} alignSelf={"flex-start"} w={"100%"} py={4}
            key={ship.publicId} borderBottom={"2px solid"} borderColor={"rgb(255,255,255,0.3)"}
    >
      <VStack  alignItems={"flex-start"} flexGrow={4}>
        <span>Ship name: {ship.name}</span>
        <span>Ship mmsi: {ship.mmsi}</span>
      </VStack>

      <HStack>
        <Tooltip label="stop tracking">
          <ShipInfoModal ship={props.ship}/>
        </Tooltip>

        <Tooltip label="show history">
          <IconButton aria-label="" bg={"teal.700"} icon={
            state === State.NOT_VISIBLE? <ViewIcon/>:
              state === State.VISIBLE? <ViewOffIcon/>:
                <SpinnerIcon/>
          }
              onClick={onVisibilityChange}
          />
        </Tooltip>

      </HStack>
    </HStack>
  );
};

export default ShipExplorerRow;