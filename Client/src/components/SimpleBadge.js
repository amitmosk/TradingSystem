import * as React from 'react';
import Badge from '@mui/material/Badge';
import MailIcon from '@mui/icons-material/Mail';

export default function SimpleBadge({num}) {
  return (
    <Badge badgeContent={num} color="primary">
      <MailIcon color="white" />
    </Badge>
  );
}
