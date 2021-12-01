import * as React from "react"
import {
  ChakraProvider,
  theme,
  Center,
} from "@chakra-ui/react"
import ShipMapComponent from "./components/ShipMapComponent"

export const App = () => (
  <ChakraProvider theme={theme}>
    <Center w="100vw" h="100vh">
      <ShipMapComponent/>
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
