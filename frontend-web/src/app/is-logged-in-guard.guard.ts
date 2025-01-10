import { CanActivateFn } from '@angular/router';

export const isLoggedInGuardGuard: CanActivateFn = (route, state) => {
  const userId = localStorage.getItem('userId');
  const userRole = localStorage.getItem('userRole');
  return !!userId && !!userRole;
};
