import {
  Alert,
  Button, Center, CloseButton, HStack,
  Input,
  Modal,
  ModalBody, ModalCloseButton,
  ModalContent, ModalFooter,
  ModalHeader,
  ModalOverlay, useBoolean,
  useDisclosure, VStack
} from '@chakra-ui/react';
import React, {useState} from 'react';
import login from "../../logic/fetchers/Login";
import {Authentication} from "./UserModuleWrapper";
import RegisterModal from "./RegisterModal";

const LoginModal = (props: {onLogin: (auth: Authentication) => any}) => {
  const {isOpen, onOpen, onClose} = useDisclosure();
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [lastLoginFailed, setFailed] = useBoolean(false);

  const onLoginButton = () => {
    login(username, password).then(response => {
      if(response.status < 300 && response.status >=200 && response.username !== undefined && response.authToken !== undefined) {
        onClose();
        props.onLogin({username: response.username, authToken: response.authToken});
      } else {
        setFailed.on();
      }
    })
  }

  return (
    <>
      <Button onClick={onOpen} size={'lg'} bg={"teal.600"}>Login</Button>
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay/>
        <ModalContent>
          <ModalCloseButton onClick={onClose}/>
          <ModalHeader><Center>Login</Center></ModalHeader>
          <ModalBody>
            <VStack py={3} spacing={6}>
              <Input placeholder={"Enter username"} onChange={(e: any) => setUsername(e.target.value)}/>
              <Input type='password' placeholder="Enter password" onChange={(e: any) => setPassword(e.target.value)}/>
              {lastLoginFailed &&
                <Alert status='error'>We could not log you, check you username and password</Alert>
              }
            </VStack>
          </ModalBody>
          <ModalFooter>
            <HStack>
              <RegisterModal/>
              <Button size="md" onClick={onLoginButton}>Login</Button>
            </HStack>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default LoginModal;