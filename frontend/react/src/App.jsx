import {
    createBrowserRouter, createRoutesFromElements, Route,
    RouterProvider
} from 'react-router-dom'
import './App.css'
import RootLayout from "./layouts/RootLayout.jsx";
import React from "react";
import Dashboard from "./pages/Dashboard.jsx";
import TenantPage from "./pages/TenantPage.jsx";

const router = createBrowserRouter(
    createRoutesFromElements(
            <Route path="/" element={<RootLayout />}>
                <Route index element={<Dashboard />} />
                <Route path="tenants" element={<TenantPage />} />
            </Route>
    )
)

function App() {
  return (
      <RouterProvider router={router} />
  )
}

export default App
