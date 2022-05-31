
     
import React from 'react';
import { useParams } from 'react-router-dom';
import ProductPage from './ProductPage';
export default function ProductPageNevigator() {
    const { product_id,store_id} = useParams();
    console.log("ProductPageNevigator->product id ="+product_id);
    console.log("ProductPageNevigator->store id ="+store_id);
    return (<ProductPage product_id={product_id} store_id={store_id}></ProductPage>);
    }






