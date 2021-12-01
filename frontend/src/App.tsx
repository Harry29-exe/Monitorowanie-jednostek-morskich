import * as React from "react"
import {
  ChakraProvider,
  theme,
  Center,
  Box,
} from "@chakra-ui/react"
import ShipMapComponent from "./components/ShipMapComponent"
import ShipModule from "./components/ShipModule";

export const App = () => (
  <ChakraProvider theme={theme}>
    <Box></Box>
    <Center w="100vw" h="100vh">
      <ShipModule/>
    </Center>
  </ChakraProvider>
)

//     <Box textAlign="center" fontSize="xl">
//     <Grid minH="100vh" p={3}>
//     <ColorModeSwitcher justifySelf="flex-end" />
//     <VStack spacing={8}>
//     <Logo h="40vmin" pointerEvents="none" />
//     <Text>
//     Edit <Code fontSize="xl">src/App.tsx</Code> and save to reload.
// </Text>
// <Link
//     color="teal.500"
//     href="https://chakra-ui.com"
//     fontSize="2xl"
//     target="_blank"
//     rel="noopener noreferrer"
// >
//   Learn Chakra
// </Link>
// </VStack>
// </Grid>
// </Box>
