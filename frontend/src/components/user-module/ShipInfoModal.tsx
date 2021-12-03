import { QuestionIcon } from '@chakra-ui/icons';
import {
  Box,
  Button, Divider, HStack,
  IconButton,
  Modal,
  ModalBody, ModalCloseButton,
  ModalContent,
  ModalHeader,
  ModalOverlay,
  useDisclosure,
  VStack
} from '@chakra-ui/react';
import React, {useContext, useState} from 'react';
import {ShipDTO} from "../../logic/dto/ships/ShipDTO";
import {LocationDTO} from "../../logic/dto/LocationDTO";
import {AuthContext} from "../../logic/contexts/AuthContext";
import deleteShip from "../../logic/fetchers/DeleteShip";
import fetchShipHistory from "../../logic/fetchers/FetchShipHistory";
import {ShipExplorerContext} from "./ShipExplorer";

const ShipInfoModal = (props: {ship: ShipDTO}) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [shipHistory, setShipHistory] = useState<LocationDTO[]>();
  const authContext = useContext(AuthContext);
  const shipExplorerCtx = useContext(ShipExplorerContext);

  const ship = props.ship;

  return (
    <>
      <IconButton icon={<QuestionIcon/>} aria-label="show details" bg={"teal.700"} onClick={onOpen}>Open Modal</IconButton>

      <Modal isOpen={isOpen} onClose={onClose} size="2xl">
        <ModalOverlay/>
        <ModalContent bg="teal.700">
          <ModalHeader>{ship.name}<ModalCloseButton/></ModalHeader>
          <ModalBody>

            <VStack w="100%">
              <HStack w="100%">
                <Box flexGrow={4}>Name:</Box>
                <Box flexGrow={0}>{ship.name}</Box>
              </HStack>
              <HStack w="100%">
                <Box flexGrow={4}>MMSI:</Box>
                <Box flexGrow={0}>{ship.mmsi}</Box>
              </HStack>
              <HStack w="100%">
                <Box flexGrow={4}>Ship type:</Box>
                <Box flexGrow={0}>{ship.shipType}</Box>
              </HStack>
              <HStack w="100%">
                <Box flexGrow={4}>Is still tracked:</Box>
                <Box flexGrow={0}>{ship.stillTracked}</Box>
              </HStack>

              <HStack py={4} w="100%" justifyContent="flex-end">
                {/*<Button onClick={() => {*/}
                {/*  if(!authContext.auth) return;*/}
                {/*  stopTrackingShip(authContext.auth.authToken, props.ship.publicId)*/}
                {/*}}>Stop tracking</Button>*/}
                <Button onClick={() => {
                  if(!authContext.auth) return;
                  deleteShip(authContext.auth.authToken, props.ship.publicId)
                    .then(success => {
                      if(success) {
                        onClose();
                        shipExplorerCtx.initShipFetch();
                      }
                    })
                }}>Delete</Button>
              </HStack>

              <Divider/>

              <Button onClick={() => {
                if(!authContext.auth) return;
                fetchShipHistory(authContext.auth.authToken, props.ship.publicId)
                  .then(shipWithHistory => setShipHistory(shipWithHistory.history));
              }}>
                Show history
              </Button>
              {shipHistory &&
                <VStack>
                  {
                    shipHistory.map(l => (
                      <HStack w="100%">
                        <Box w="25%">X: {l.x}</Box>
                        <Box w="25%">Y: {l.y}</Box>
                        <Box w="50%">Date: {l.time}</Box>
                      </HStack>
                    ))
                  }
                </VStack>
              }

            </VStack>

          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  );
};

export default ShipInfoModal;