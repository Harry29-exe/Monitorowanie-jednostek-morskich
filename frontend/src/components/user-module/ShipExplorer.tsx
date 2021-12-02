import React, {useState} from 'react';
import {
  Box,
  Button,
  Center,
  Divider,
  HStack,
  IconButton,
  Tooltip,
  useBoolean,
  useBreakpoint, useBreakpointValue,
  VStack
} from "@chakra-ui/react";
import {ShipDTO} from "../../logic/dto/ships/ShipDTO";
import {ArrowLeftIcon, ArrowRightIcon, MinusIcon, SmallCloseIcon, ViewIcon} from "@chakra-ui/icons";
import ShipExplorerRow from "./ShipExplorerRow";
import {OperationStatus} from "./UserModuleWrapper";

const ShipExplorer = (props: {
  ships: ShipDTO[],
  show: (id: string) => Promise<OperationStatus>,
  hide: (id: string) => Promise<OperationStatus>
}) => {
  const [enabled, setEnabled] = useBoolean(true);
  const [selected, setSelected] = useState<number>();
  const w = useBreakpointValue([250, 350, 400]);

  return (
    <HStack h="100%" spacing={0} pos="absolute" top={0}
            zIndex={5} shadow="0 0 10px 4px black"
            left={enabled? 0: `-${w}px`} transition={"left 0.5s"}>
      <VStack bg={"teal.600"} px={4} h="100%" py={6} w={`${w}px`}
              pos={"relative"}
      >
        {
          props.ships.map(s => (
            <ShipExplorerRow ship={s} show={props.show} hide={props.hide}/>
          ))
        }
      </VStack>

      <Center onClick={() => setEnabled.toggle()}
              _hover={{cursor: "pointer"}} p={2} pb={40} fontSize="16px"
              w="30px" h="100%" bg={"teal.700"}>
        {enabled?
          <ArrowLeftIcon/>
          :
          <ArrowRightIcon/>
        }
      </Center>
    </HStack>
  );
};

export default ShipExplorer;