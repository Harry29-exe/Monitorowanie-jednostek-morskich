import * as React from "react"
import {
  ChakraProvider,
  Center,
  Box,
  HStack,
  Link, extendTheme,
} from "@chakra-ui/react"
import ShipModule from "./components/ShipModule";
import { useState } from "react";
import UserModuleWrapper, {Authentication} from "./components/user-module/UserModuleWrapper";
import { AuthContext } from "./logic/contexts/AuthContext";
import "./Scrolbar.css"

const config = {
  initialColorMode: 'light',
  useSystemColorMode: false,
}

// 3. extend the theme
const theme = extendTheme({ config })

export const App = () => {
  const [page, setPage] = useState<"main" | "my">("main")
  const [auth, setAuth] = useState<Authentication>();

  return (
    <ChakraProvider theme={theme}>
      <AuthContext.Provider value={{auth: auth, updateAuth: setAuth}}>
      <HStack w="100vw" h={"70px"} bg={"teal.600"} spacing={0} fontSize={"28px"} textAlign={"center"}
              shadow={"0px 0px 10px 2px black"} pos={"relative"} zIndex={10}>
        <Link bg={page === "main"? "teal.700" : "teal.600"} px={8}
              h={"100%"} onClick={() => setPage("main")}>
          <Center h={"100%"}>Ship Monitoring</Center>
        </Link>
        <Link bg={page === "my"? "teal.700" : "teal.600"} px={8}
              h={"100%"} onClick={() => setPage("my")}>
          <Center h={"100%"}>My tracked Ships</Center>
        </Link>
      </HStack>
      <Center w="100vw" h="calc(100vh - 70px)">

        {page === "main"?
          <ShipModule/>
        :
          <UserModuleWrapper/>
        }
    </Center>
      </AuthContext.Provider>
  </ChakraProvider>
)}
