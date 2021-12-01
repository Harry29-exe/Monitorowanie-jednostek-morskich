import React, {useState} from 'react';
import LoginModal from "./LoginModal";

export interface Authentication {
  username: string;
  authToken: string;
}

const UserModuleWrapper = () => {
  const [auth, setAuth] = useState<Authentication | null>(null);

  return (
    <div>
      {!auth?
        <LoginModal onLogin={setAuth}/>
        :
        <h5>"Nic"</h5>
      }


    </div>
  );
};

export default UserModuleWrapper;