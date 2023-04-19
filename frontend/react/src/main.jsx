import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {ChakraProvider} from "@chakra-ui/react";
import {createBrowserRouter, createRoutesFromElements, Route, RouterProvider} from "react-router-dom";
import RootLayout from "./layouts/RootLayout.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import TenantPage from "./pages/TenantPage.jsx";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<RootLayout />}>
            <Route path="signup" element={<Signup/>} />
            <Route path="login" element={<Login />} />
            <Route element={<Dashboard />} />
            <Route path="tenants" element={<TenantPage />} />
        </Route>
    )
)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <ChakraProvider>
          <RouterProvider router={router} />
      </ChakraProvider>
  </React.StrictMode>
)
