import React, {useContext, useState} from 'react';
import {Alert, AlertIcon, Button, CloseButton, Input, useBoolean, VStack} from "@chakra-ui/react";
import {SmallAddIcon} from "@chakra-ui/icons";
import {AuthContext} from "../../logic/contexts/AuthContext";
import trackNewShip from "../../logic/fetchers/TrackNewShip";

const ShipNewTrackInput = (props: {initShipsFetch: () => any}) => {
  const authCtx = useContext(AuthContext);
  const [lastAddSuccess, updateLastAdd] = useBoolean(false);
  const [mmsi, setMmsi] = useState<string>("");

  return (
    <VStack w={"100%"} borderBottom={"2px solid"} borderColor={"rgb(255,255,255,0.3)"} pb={5}>
      <Input placeholder="enter ship mmsi" onChange={(e: any) => setMmsi(e.target.value)}/>

      {lastAddSuccess &&
      <Alert status={"success"} borderRadius="md" bg={"green.500"}>
          <AlertIcon />
          Successfully added new ship
          <CloseButton onClick={updateLastAdd.toggle} position='absolute' right='8px' top='8px' />
      </Alert>
      }


      <Button px={2} w={"100%"} bg={"teal.700"}
              onClick={() => {
                if (!authCtx.auth) return;
                  trackNewShip(authCtx.auth.authToken, Number.parseInt(mmsi))
                    .then(status => {
                      if(status >= 200 && status < 300) {
                        updateLastAdd.on();
                        props.initShipsFetch();
                      }
                    })
                }}>
        Add ship to track
        <SmallAddIcon fontSize={"25px"}/>
      </Button>

    </VStack>
  );
};

export default ShipNewTrackInput;