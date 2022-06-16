//DELETE this page
import React, { useState } from "react";

import "./styles.css";

export default function Checkbox({name, list}) {
    console.log(name);
    console.log(list);
  // State with list of all checked item
  const [checked, setChecked] = useState([]);
  const checkList = ["Apple", "Banana", "Tea", "Coffee"];

  // Add/Remove checked item from list
  const handleCheck = (event) => {
   
    var updatedList = [...checked];
    if (event.target.checked) {
      updatedList = [...checked, event.target.value];
    } else {
      updatedList.splice(checked.indexOf(event.target.value), 1);
    }
    if(updatedList.length <= 2)
    {
        setChecked(updatedList);
    }
    
    
  };

  // Generate string of checked items
  const checkedItems = checked.length
    ? checked.reduce((total, item) => {
        return total + ", " + item;
      })
    : "";

  // Return classes based on whether item is checked
  var isChecked = (item) =>
    checked.includes(item) ? "checked-item" : "not-checked-item";

  return (
    <div className="app">
      <div className="checkList">
        <div className="title">{name}</div>
        <div className="list-container">
          {list.map((item, index) => (
            <div key={index}>
              <input value={item} type="checkbox" onChange={handleCheck} />
              <span className={isChecked(item)}>{item}</span>
            </div>
          ))}
        </div>
      </div>

      <div>
        {`Items checked are: ${checkedItems}`}
      </div>
    </div>
  );
}

