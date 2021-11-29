import * as React from "react"
import {
  ChakraProvider,
  Box,
  Text,
  Link,
  VStack,
  Code,
  Grid,
  theme,
} from "@chakra-ui/react"
import { ColorModeSwitcher } from "./ColorModeSwitcher"
import { Logo } from "./Logo"
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet"

export const App = () => (
  <ChakraProvider theme={theme}>
    <MapContainer center={[51.505, -0.09]} zoom={13} scrollWheelZoom={false} style={{height: "200px", width: "400px"}}>
      <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <Marker position={[51.505, -0.09]}>
        <Popup>
          A pretty CSS3 popup. <br /> Easily customizable.
        </Popup>
      </Marker>
    </MapContainer>

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
