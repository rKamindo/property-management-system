import {
    BrowserRouter,
    createBrowserRouter, createRoutesFromElements, Route,
    RouterProvider, Routes
} from 'react-router-dom'
import './App.css'
import RootLayout from "./layouts/RootLayout.jsx";
import React from "react";
import Dashboard from "./pages/Dashboard.jsx";

const router = createBrowserRouter(
    createRoutesFromElements(
            <Route path="/" element={<RootLayout />}>
                <Route index element={<Dashboard />} />
            </Route>
    )
)

function App() {
  return (
      <RouterProvider router={router} />
  )
}

export default App
