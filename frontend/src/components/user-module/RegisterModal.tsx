import React, {useState} from 'react';
import {
  Alert,
  Button, Center, CloseButton, Input,
  Modal,
  ModalBody, ModalCloseButton,
  ModalContent, ModalFooter,
  ModalHeader,
  ModalOverlay, useBoolean,
  useDisclosure,
  VStack
} from "@chakra-ui/react";
import login from "../../logic/fetchers/Login";
import register from '../../logic/fetchers/Register';

const RegisterModal = () => {
  const {isOpen, onOpen, onClose} = useDisclosure();
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [registered, setRegistered] = useBoolean(false);
  const [lastLoginFailed, setFailed] = useBoolean(false);


  const onRegisterButton = () => {
    register(username, password).then(status => {
      if(status < 300 && status >=200) {
        setRegistered.on();
      } else {
        setFailed.on();
      }
    })
  }

  return (
    <>
      <Button onClick={onOpen}>Register</Button>
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay/>
        <ModalContent>
          {!registered ?
            <>
              <ModalCloseButton onClick={onClose}/>
              <ModalHeader><Center>Register</Center></ModalHeader>
              <ModalBody>
                <VStack py={3} spacing={6}>
                  <Input placeholder={"Enter username"} onChange={(e: any) => setUsername(e.target.value)}/>
                  <Input type='password' placeholder="Enter password" onChange={(e: any) => setPassword(e.target.value)}/>
                  {lastLoginFailed &&
                    <Alert status='error'>We could not register your account try with different username/password</Alert>
                  }
                </VStack>
              </ModalBody>
              <ModalFooter>
                <Button onClick={onRegisterButton} size="md">Register</Button>
              </ModalFooter>
            </>
            :
            <>
              <Alert status='success'>You have been successfully registered</Alert>
              <Button onClick={onClose}>Take me to login</Button>
            </>
          }
        </ModalContent>
      </Modal>
    </>
  );
};

export default RegisterModal;